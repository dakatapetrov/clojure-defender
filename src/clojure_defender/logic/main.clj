(ns clojure-defender.logic.main
  (:use [clojure-defender.logic spawner
                                enemy
                                building
                                projectile
                                level]
        [clojure-defender.levels.level-01]
        [clojure-defender.physics.geometry])
  (:require [clojure-defender.globals :as gl]
            [clojure-defender.data.buildings :as db]))

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
  (load-level world
              paths
              defend-points
              build-areas
              spawners
              lives
              funds)
  (loop []
    (when @gl/playing?
      (cooldown-timer @gl/spawners)
      (cooldown-timer @gl/buildings)
      (step gl/spawners step-spawner)
      (step gl/enemies step-enemy)
      (step gl/buildings shoot)
      (step gl/projectiles step-projectile))
    (Thread/sleep 6)
    (recur)))
