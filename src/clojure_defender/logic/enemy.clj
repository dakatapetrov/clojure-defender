(ns clojure-defender.logic.enemy
  (:use [clojure-defender.physics enemy geometry])
  (:require [clojure-defender.globals :as gl]))

(defn- path-direction
  [x y]
  (let [on-path (on-object x y @gl/paths)
        [dir-x dir-y] (:direction on-path)]
    [dir-x dir-y]))

(defn- on-defend-point?
  [x y]
  (on-object x y @gl/defend-points))

(defn- move-by
  [dir-x dir-y speed]
  (let [move-by-x (* speed dir-x)
        move-by-y (* speed dir-y)]
    [move-by-x move-by-y]))

(defn- dead?
  [enemy]
  (let [hp (:hp @enemy)]
    (<= hp 0)))

(defn- lose-life
  []
  (swap! gl/lives dec))

(defn- loot-enemy
  [enemy]
  (let [loot (:loot @enemy)]
    (swap! gl/funds #(+ % loot))))

(defn- process-debuff
  [debuff enemy]
  (let [{:keys [clear duration]} @debuff]
    (if (<= duration 0)
      (dosync
        (clear enemy)
        (alter enemy dissoc :debuff))
      (dosync
        (alter debuff update-in [:duration] dec)))))

(defn step-enemy
  [enemy]
  (let [{:keys [speed x y debuff]} @enemy
        [dir-x dir-y] (path-direction x y)
        [move-by-x move-by-y] (move-by dir-x dir-y speed)]
    (when debuff
      (process-debuff debuff enemy))
    (cond
      (dead? enemy) (do
                      (loot-enemy enemy)
                      (kill-enemy enemy))
      (on-defend-point? x y) (do
                               (kill-enemy enemy)
                               (lose-life))
      :else (move-enemy enemy move-by-x move-by-y))))
