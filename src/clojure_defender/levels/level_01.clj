(ns clojure-defender.levels.level-01
  (:use [clojure-defender.logic.level])
  (:require [clojure-defender.globals :as gl]
            [clojure-defender.data.enemies :as ed]))

(def lives 20)

(def funds 200.0)

(def time-limit 150)

(def world (define-world 980 768 "#3eb677"))

(def path1
  (for [x (range 0 700 30)
        y [100 490]]
    (define-path x y [1 0] "#e39e54")))

(def path2 [(define-path 720 100 [0.5 1] "#e39e54")])

(def path3
  (for [x [720]
        y (range 130 500 30)]
    (define-path x y [0 1] "#e39e54")))

(def paths (merge-defined path1 path2 path3))

(def defend-points [(define-defend-point 720 490 50 :red)])

(def build-areas
  (concat (for [x (range 0 700 90)
                y [70 130]]
            (define-build-area x y "#f4dd65"))
          (for [x (range 0 700 90)
                y [460 520]]
            (define-build-area x y "#f4dd65"))
          (for [x [690 750]
                y (range 130 461 90)]
            (define-build-area x y "#f4dd65"))))

(def spawners
  [(define-spawner 0 110 [ed/assassin ed/zombie ed/orc] 300)
   (define-spawner 0 500 [ed/assassin ed/zombie] 1000)])
