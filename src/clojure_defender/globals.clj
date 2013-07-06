(ns clojure-defender.globals)

(use 'clojure.repl)
(use 'seesaw.core)
(require '[clojure-defender.data.buildings :as db])

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
(def main-frame (frame :title "Clojure Defender"
                       :on-close :exit
                       :size [window-width :by window-height]))
(def click-location (ref {}))
(def current-building (atom db/fire-tower))
(def playing? (atom false))
