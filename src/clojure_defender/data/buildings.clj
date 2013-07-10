(ns clojure-defender.data.buildings)

(require '[clojure-defender.data.projectiles :as pj])

(defn define-building
  [fire-range cooldown projectiles color]
  {:fire-range fire-range :cooldown cooldown
   :projectiles projectiles :color color})

(defn modify-building
  [building attributes]
  (conj building attributes))

(def fire-tower (define-building 70 300 [pj/fireball] :orange))
(def frost-tower (define-building 70 350 [pj/frostball] :blue))
(def arcane-tower (define-building 65 250 [pj/arcanemissle] :gray))
(def ultimate-tower (define-building 70
                                   280
                                   [pj/fireball pj/frostball pj/arcanemissle]
                                   :black))
