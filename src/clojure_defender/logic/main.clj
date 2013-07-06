(ns clojure-defender.logic.main)

(require '[clojure-defender.globals :as gl])
(require '[clojure-defender.data.buildings :as db])

(defn location
  [object]
  (let [{:keys [x y]} object]
    [x y]))

(defn in-rect?
  [x y w h ox oy]
  (let [not-pos? (complement pos?)
        not-neg? (complement neg?)]
    (and (not-pos? (compare x ox))
         (not-neg? (compare (+ x w) ox))
         (not-pos? (compare y oy))
         (not-neg? (compare (+ y h) oy)))))

(defn on-object
  [x y objects]
  (first (filter #(in-rect? (:x %)
                            (:y %)
                            gl/path-size
                            gl/path-size
                            x
                            y)
                 objects)))

(defn move-enemy
  [enemy]
  (let [{:keys [speed hp x y]} @enemy
        on-path (on-object x y @gl/paths)
        [px py] (:direction on-path)
        on-defend-point (on-object x y @gl/defend-points)
        movex (* speed px)
        movey (* speed py)]
    (if (or (<= hp 0) on-defend-point)
      (swap! gl/enemies disj enemy)
      (dosync
        (alter enemy update-in [:x] #(+ % movex))
        (alter enemy update-in [:y] #(+ % movey))))))

(defn step
  []
  (doseq [enemy @gl/enemies]
    (move-enemy enemy)))

(defn cooldown-timer
  [col]
  (doseq [obj col]
    (let [ac (:ac obj)]
      (if @gl/playing?
        (when (pos? @ac)
          (swap! ac dec))))))


(defn spawn
  []
  (doseq [spawner @gl/spawners]
    (let [ac (:ac spawner)]
      (when-not (pos? @ac)
        (let [{:keys [x y cooldown]} spawner
              enemy (rand-nth (:enemies spawner))]
          (swap! gl/enemies
                 conj
                 (ref (conj enemy {:x x :y y})))
          (reset! ac cooldown))))))

(defn build
  []
  (let [[cx cy] (location @gl/click-location)
        on-build-area (on-object cx cy @gl/build-areas)
        [x y] (location on-build-area)
        on-building (on-object cx cy @gl/buildings)]
    (when (and x y)
      (if-not on-building
        (dosync
          (swap! gl/buildings conj (conj @gl/current-building
                                         {:x x :y y :ac (atom 0)}))
          (alter gl/click-location conj {:x nil :y nil}))
        (dosync
          (swap! gl/buildings disj on-building)
          (alter gl/click-location conj {:x nil :y nil}))))))

(defn abs
  [x]
  (if (neg? x) (- x) x))

(defn get-direcction
  [x y ox oy]
  (let [vx (- x ox)
        vy (- y oy)]
    (if (< (abs vx) (abs vy))
      [(/ vx (abs vy)) (/ vy (abs vy))]
      [(/ vx (abs vx)) (/ vy (abs vx))])))

(defn enemies-in-range
  [x y r]
  (filter #(let [[ex ey] (location @%)]
             (<= (+ (* (- ex x)
                       (- ex x))
                    (* (- ey y)
                       (- ey y)))
                 (* r r)))
          @gl/enemies))

(defn rect-center
  [x y w h]
  (let [cx (+ x w)
        cy (+ y h)]
    [cx cy]))


(defn shoot
  []
  (doseq [building @gl/buildings]
    (let [[x y] (location building)
          [cx cy] (rect-center x y 15 15)
          cd (:cooldown building)
          ac (:ac building)
          projectile (-> building :projectiles rand-nth)
          fire-range (:fire-range building)
          enemies (enemies-in-range x y fire-range)
          one (first enemies)]
      (when (and one (not (pos? @ac)))
        (swap! gl/projectiles conj (ref (conj projectile
                                              {:x cx :y cy :enemy one})))
        (reset! ac cd)))))

(defn move-projectiles
  []
  (doseq [projectile @gl/projectiles]
    (dosync
      (let [{:keys [x y speed enemy]} @projectile
            [ex ey] (location @enemy)
            [cx cy] (rect-center ex ey 5 5)
            [dirx diry] (get-direcction cx cy x y)]
        (alter projectile update-in [:x] #(+ % (* speed dirx)))
        (alter projectile update-in [:y] #(+ % (* speed diry)))))))

(defn collisions
  []
  (doseq [p @gl/projectiles]
    (let [{:keys [x y damage enemy]} @p
          [ex ey] (location @enemy)
          [cx cy] (rect-center ex ey 5 5)
          dx (* (- x cx) (- x cx))
          dy (* (- y cy) (- y cy))
          d (+ dx dy)]
      (when (<= d 10)
        (dosync
          (alter enemy update-in [:hp] #(- % damage))
          (swap! gl/projectiles disj p))))))


(defn play
  []
  (cooldown-timer @gl/spawners)
  (cooldown-timer @gl/buildings)
  (spawn)
  (build)
  (step)
  (shoot)
  (move-projectiles)
  (collisions))
