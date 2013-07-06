(ns clojure-defender.data.enemies)

(defn make-enemy
  [speed hp color]
  {:speed speed :hp hp :color color})

(def assassin (make-enemy 0.2 70 :black))
(def zombie (make-enemy 0.1 80 :gray))
(def orc (make-enemy 0.15 120 :green))
