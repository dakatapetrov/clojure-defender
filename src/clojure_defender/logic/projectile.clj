(ns clojure-defender.logic.projectile)

(require '[clojure-defender.globals :as gl])
(use 'clojure-defender.physics.geometry)

(defn move-projectiles
  []
  (doseq [projectile @gl/projectiles]
    (dosync
      (let [{:keys [x y speed enemy]} @projectile
            ex (:x @enemy)
            ey (:y @enemy)
            [cx cy] (rect-center ex ey gl/enemy-size gl/enemy-size)
            [svx svy] (sub-vect x y cx cy)
            [dirx diry] (normalized-vector svx svy)]
        (alter projectile update-in [:x] #(+ % (* speed dirx)))
        (alter projectile update-in [:y] #(+ % (* speed diry)))))))

(defn collisions
  []
  (doseq [p @gl/projectiles]
    (let [{:keys [x y damage enemy]} @p
          ex (:x @enemy)
          ey (:y @enemy)
          [cx cy] (rect-center ex ey gl/enemy-size gl/enemy-size)
          hs (/ gl/enemy-size 2)]
      (when (sphere-colision? x y gl/projectile-size cx cy hs)
        (dosync
          (alter enemy update-in [:hp] #(- % damage))
          (swap! gl/projectiles disj p))))))
