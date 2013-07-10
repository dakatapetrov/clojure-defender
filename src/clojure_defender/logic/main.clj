(ns clojure-defender.logic.main)

(require '[clojure-defender.globals :as gl])
(require '[clojure-defender.data.buildings :as db])
(use 'clojure-defender.logic.spawner)
(use 'clojure-defender.logic.enemy)
(use 'clojure-defender.logic.building)
(use 'clojure-defender.logic.projectile)
(use 'clojure-defender.physics.geometry)

(defn step
  [col func]
  (doseq [object @col]
    (func object)))

(defn cooldown-timer
  [col]
  (doseq [obj col]
    (let [ac (:ac obj)]
      (if @gl/playing?
        (when (pos? @ac)
          (swap! ac dec))))))

(defn play
  []
  (cooldown-timer @gl/spawners)
  (cooldown-timer @gl/buildings)
  (step gl/spawners step-spawner)
  (build)
  (step gl/enemies step-enemy)
  (shoot)
  (move-projectiles)
  (collisions))
