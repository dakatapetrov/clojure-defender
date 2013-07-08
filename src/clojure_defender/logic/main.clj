(ns clojure-defender.logic.main)

(require '[clojure-defender.globals :as gl])
(require '[clojure-defender.data.buildings :as db])
(use 'clojure-defender.logic.spawner)
(use 'clojure-defender.logic.enemy)
(use 'clojure-defender.logic.building)
(use 'clojure-defender.physics.geometry)

(defn location
  [object]
  (let [{:keys [x y]} object]
    [x y]))

(defn step
  []
  (doseq [enemy @gl/enemies]
    (step-enemy enemy)))

(defn cooldown-timer
  [col]
  (doseq [obj col]
    (let [ac (:ac obj)]
      (if @gl/playing?
        (when (pos? @ac)
          (swap! ac dec))))))


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

(defn move-projectiles
  []
  (doseq [projectile @gl/projectiles]
    (dosync
      (let [{:keys [x y speed enemy]} @projectile
            [ex ey] (location @enemy)
            [cx cy] (rect-center ex ey gl/enemy-size gl/enemy-size)
            [svx svy] (sub-vect x y cx cy)
            [dirx diry] (normalized-vector svx svy)]
        (alter projectile update-in [:x] #(+ % (* speed dirx)))
        (alter projectile update-in [:y] #(+ % (* speed diry)))))))

(defn collisions
  []
  (doseq [p @gl/projectiles]
    (let [{:keys [x y damage enemy]} @p
          [ex ey] (location @enemy)
          [cx cy] (rect-center ex ey gl/enemy-size gl/enemy-size)
          hs (/ gl/enemy-size 2)]
      (when (sphere-colision? x y gl/projectile-size cx cy hs)
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
