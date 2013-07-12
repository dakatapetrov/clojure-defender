(ns clojure-defender.logic.level
  (:use [clojure-defender.globals])
  (:require [clojure-defender.globals :as gl]))

(defn define-world
  [width height color]
  {:x 0 :y 0 :width width :height height :color color})

(defn define-path
  [x y direction color]
  {:x x :y y :direction direction :color color})

(defn merge-defined
  [& args]
  (vec (apply concat args)))

(defn define-defend-point
  [x y capacity color]
  {:x x :y y :capacity capacity :color color})

(defn define-build-area
  [x y color]
  {:x x :y y :color color})

(defn define-spawner
  [x y enemies cooldown]
  {:x x :y y :enemies enemies :cooldown cooldown :ac (atom 0)})

(defn load-world
  [world]
  (swap! gl/world conj world))

(defn load-paths
  [paths]
  (doseq [path paths]
    (swap! gl/paths conj path)))

(defn load-defend-points
  [defend-points]
  (doseq [protect-point defend-points]
    (swap! gl/defend-points conj protect-point)))

(defn load-build-areas
  [build-areas]
  (doseq [build-area build-areas]
    (swap! gl/build-areas conj build-area)))

(defn load-spawners
  [spawners]
  (doseq [spawner spawners]
    (swap! gl/spawners conj spawner)))

(defn load-lives
  [lives]
  (reset! gl/lives lives))

(defn load-funds
  [funds]
  (reset! gl/funds funds))

(defn load-level
  [world paths defend-points build-areas spawners lives funds]
  (load-world world)
  (load-paths paths)
  (load-defend-points defend-points)
  (load-build-areas build-areas)
  (load-spawners spawners)
  (load-lives lives)
  (load-funds funds))
