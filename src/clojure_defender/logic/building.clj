(ns clojure-defender.logic.building)

(require '[clojure-defender.globals :as gl])
(use 'clojure-defender.physics.geometry)

(defn- enemies-in-range
  [x y r]
  (filter #(let [ex (:x @%)
                 ey (:y @%)]
             (in-range? ex ey x y r))
          @gl/enemies))

(defn- build
  [x y]
  (swap! gl/buildings conj (conj @gl/current-building
                                 {:x x :y y :ac (atom 0)})))
(defn- on-cooldown?
  [building]
  (let [ac (:ac building)]
    (pos? @ac)))

(defn- reset-cooldown
  [building]
  (let [{:keys [ac cooldown]} building]
    (reset! ac cooldown)))

(defn- destroy
  [building]
  (swap! gl/buildings disj building))

(defn- spawn-projectile
  [x y projectile enemy]
  (swap! gl/projectiles conj (ref (conj projectile
                                        {:x x :y y :enemy enemy}))))
(defn shoot
  [building]
  (let [{:keys [x y projectiles fire-range]} building
        [cx cy] (rect-center x y gl/building-size gl/building-size)
        projectile (rand-nth projectiles)
        enemies (enemies-in-range cx cy fire-range)
        enemy (first enemies)]
    (when (and enemy (not (on-cooldown? building)))
      (dosync
        (spawn-projectile cx cy projectile enemy)
        (reset-cooldown building)))))

(defn build-or-destroy
 [cx cy]
 (let [on-build-area (on-object cx cy @gl/build-areas)
       {:keys [x y]} on-build-area
       on-building (on-object cx cy @gl/buildings)]
   (if on-building
     (destroy on-building)
     (when on-build-area
       (build x y)))))
