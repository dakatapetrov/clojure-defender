(ns clojure-defender.data.enemies)

(defn define-enemy
  [speed hp color]
  {:speed speed :hp hp :color color})

(defn modify-enemy
  [enemy attributes]
  (conj enemy attributes))

(def assassin (define-enemy 0.2 70 :black))
(def zombie (define-enemy 0.1 80 :gray))
(def orc (define-enemy 0.15 120 :green))
