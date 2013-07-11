(ns clojure-defender.data.enemies)

(defn define-enemy
  [speed hp loot color]
  {:speed speed :hp hp :loot loot :color color})

(defn modify-enemy
  [enemy attributes]
  (conj enemy attributes))

(def assassin (define-enemy 0.2 70 20 :black))
(def zombie (define-enemy 0.1 80 18 :gray))
(def orc (define-enemy 0.15 120 22 :green))
