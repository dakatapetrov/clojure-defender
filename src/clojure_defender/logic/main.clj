(ns clojure-defender.logic.main
  (:use [clojure-defender.logic spawner
                                enemy
                                building
                                projectile
                                level]
        [clojure-defender.levels.level-01]
        [clojure-defender.physics.geometry]
        [clojure-defender.gui.main]
        [seesaw.core])
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

(defn lost?
  []
  (<= @gl/lives 0))

(defn reset-world
  []
  (dosync
    (reset! gl/paths [])
    (reset! gl/defend-points [])
    (reset! gl/spawners [])
    (reset! gl/build-areas [])
    (reset! gl/buildings #{})
    (reset! gl/enemies #{})
    (reset! gl/projectiles #{})
    (reset! gl/lives 0)
    (reset! gl/funds 0.0)
    (reset! gl/world {:x 0 :y 0 :width 0 :height 0 :color :green})))

(defn load-level-01
  []
  (load-level world
              paths
              defend-points
              build-areas
              spawners
              lives
              funds))

(defn play
  []
  (load-level-01)
  (create-gui)
  (future (redraw))
  (loop []
    (when (lost?)
      (reset! gl/playing? false)
      (alert "Losers gonna lose!")
      (reset-world)
      (load-level-01))
    (when @gl/playing?
      (cooldown-timer @gl/spawners)
      (cooldown-timer @gl/buildings)
      (step gl/spawners step-spawner)
      (step gl/enemies step-enemy)
      (step gl/buildings shoot)
      (step gl/projectiles step-projectile))
    (Thread/sleep 6)
    (recur)))
