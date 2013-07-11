(ns clojure-defender.core
  (:gen-class)
  (:use [clojure.repl]
        [clojure-defender.levels.level-01]
        [clojure-defender.gui.main])
  (:require [clojure-defender.globals :as gl]))

(defn -main
  [& args]
  (load-level)
  (create-gui)
  (redisplay gl/main-frame)
  (run))
