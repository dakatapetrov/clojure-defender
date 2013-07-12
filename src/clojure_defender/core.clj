(ns clojure-defender.core
  (:gen-class)
  (:use [clojure-defender.gui.main])
  (:require [clojure-defender.globals :as gl]))

(defn -main
  [& args]
  (create-gui)
  (redisplay gl/main-frame)
  (run))
