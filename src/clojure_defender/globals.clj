(ns clojure-defender.globals)

(use 'clojure.repl)
(use 'seesaw.core)

(def paths (atom []))
(def protect-points (atom []))
(def build-areas (atom []))
(def generators (atom []))
(def buildings (atom []))
(def enemies (atom {}))
(def projectiles (atom []))

(def window-width 800.0)
(def window-height 600.0)
(def path-size 30.0)
(def building-size 30.0)
(def enemy-size 10.0)
(def enemy-max-speed 1.0)
(def projectiles-max-speed 0.0)
(def main-frame (frame :title "Clojure Defender"
                       :size [window-width :by window-height]))
