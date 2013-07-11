(ns clojure-defender.physics.enemy
  (:require [clojure-defender.globals :as gl]))

(defn kill-enemy
  [enemy]
  (swap! gl/enemies disj enemy))

(defn move-enemy
  [enemy x y]
  (dosync
    (alter enemy update-in [:x] #(+ % x))
    (alter enemy update-in [:y] #(+ % y))))
