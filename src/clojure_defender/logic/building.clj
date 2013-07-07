(ns clojure-defender.logic.building)

(require '[clojure-defender.globals :as gl])
(use 'clojure-defender.physics.geometry)

(defn enemies-in-range
  [x y r]
  (filter #(let [ex (:x @%)
                 ey (:y @%)]
             (in-range? ex ey x y r))
          @gl/enemies))


(defn shoot
  []
  (doseq [building @gl/buildings]
    (let [{:keys [x y cooldown ac projectiles fire-range]} building
          [cx cy] (rect-center x y gl/building-size gl/building-size)
          projectile (rand-nth projectiles)
          enemies (enemies-in-range x y fire-range)
          one (first enemies)]
      (when (and one (not (pos? @ac)))
        (swap! gl/projectiles conj (ref (conj projectile
                                              {:x cx :y cy :enemy one})))
        (reset! ac cooldown)))))
