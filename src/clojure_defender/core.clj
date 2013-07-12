(ns clojure-defender.core
  (:gen-class)
  (:use [clojure-defender.logic.main])
  (:require [clojure-defender.globals :as gl]))

(defn -main
  [& args]
  (play))
