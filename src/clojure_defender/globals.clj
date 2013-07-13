(ns clojure-defender.globals
  (:require [seesaw.core :as sc]
            [clojure-defender.data.buildings :as db]))

(def paths (atom []))
(def defend-points (atom []))
(def build-areas (atom []))
(def spawners (atom []))
(def buildings (atom #{}))
(def enemies (atom #{}))
(def projectiles (atom #{}))

(def window-width 1024)
(def window-height 768)
(def path-size 30.0)
(def building-size 30.0)
(def enemy-size 10.0)
(def projectile-size 3.0)
(def main-frame (sc/frame :title "Clojure Defender"
                          :on-close :exit
                          :size [window-width :by window-height]))
(def current-building (atom db/fire-tower))
(def playing? (atom false))
(def lives (atom 0))
(def funds (atom 0.0))
(def world (atom {:x 0 :y 0 :width 0 :height 0 :color :green}))
(def timer (atom 0))
