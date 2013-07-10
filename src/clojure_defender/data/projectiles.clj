(ns clojure-defender.data.projectiles)

(defn define-projectile
  [speed damage color]
  {:speed speed :damage damage :color color})

(defn modify-projectile
  [projectile attributes]
  (conj projectile attributes))

(def fireball (define-projectile 0.3 40 :yellow))
(def frostball (define-projectile 0.25 45 :aqua))
(def arcanemissle (define-projectile 0.35 35 :white))
