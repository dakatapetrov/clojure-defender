(ns clojure-defender.data.buildings)

(require '[clojure-defender.data.projectiles :as pj])

(defn define-building
  [cost fire-range cooldown projectiles color]
  {:cost cost :fire-range fire-range :cooldown cooldown
   :projectiles projectiles :color color})

(defn modify-building
  [building attributes]
  (conj building attributes))

(def fire-tower (define-building 100 70 300 [pj/fireball] :orange))
(def frost-tower (define-building 110 70 350 [pj/frostball pj/frostball pj/frostball pj/slow-frostball] :blue))
(def arcane-tower (define-building 90 65 250 [pj/arcanemissle] :gray))
(def ultimate-tower (define-building 100 70
                                   280
                                   [pj/fireball pj/frostball pj/arcanemissle]
                                   :black))
