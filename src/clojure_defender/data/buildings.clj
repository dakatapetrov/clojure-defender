(ns clojure-defender.data.buildings)

(require '[clojure-defender.data.projectiles :as pj])

(defn make-building
  [fire-range cooldown projectiles color]
  {:fire-range fire-range :cooldown cooldown
   :projectiles projectiles :color color})

(def fire-tower (make-building 70 300 [pj/fireball] :orange))
(def frost-tower (make-building 70 350 [pj/frostball] :blue))
(def arcane-tower (make-building 65 250 [pj/arcanemissle] :gray))
(def ultimate-tower (make-building 70
                                   280
                                   [pj/fireball pj/frostball pj/arcanemissle]
                                   :black))
