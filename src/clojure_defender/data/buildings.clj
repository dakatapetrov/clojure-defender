(ns clojure-defender.data.buildings
  (:require [clojure-defender.data.projectiles :as pj]))

(defn define-building
  [cost fire-range cooldown projectiles color]
  {:cost cost :fire-range fire-range :cooldown cooldown
   :projectiles projectiles :color color})

(defn modify-building
  [building attributes]
  (conj building attributes))

(def fire-tower (define-building 100 70 150 [pj/fireball] :orange))
(def frost-tower (define-building 110 70 175
                                  [pj/frostball
                                   pj/frostball
                                   pj/frostball
                                   pj/slow-frostball]
                                  "#4f81bd"))
(def arcane-tower (define-building 90 65 125 [pj/arcanemissle] :gray))
(def ultimate-tower (define-building 100 70 140
                                     [pj/fireball
                                      pj/frostball
                                      pj/arcanemissle]
                                     :black))
