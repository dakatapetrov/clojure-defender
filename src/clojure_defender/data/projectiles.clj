(ns clojure-defender.data.projectiles)

(defn make-projectile
  [speed damage color]
  {:speed speed :damage damage :color color})


(def fireball (make-projectile 0.3 40 :yellow))
(def frostball (make-projectile 0.25 45 :aqua))
(def arcanemissle (make-projectile 0.35 35 :white))
