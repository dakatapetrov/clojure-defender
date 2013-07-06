(ns clojure-defender.core
  (:gen-class))

(use 'clojure.repl)
(use '[seesaw core color graphics behave keymap])
(require '[clojure-defender.globals :as gl])
(use 'clojure-defender.gui.main)
(use 'clojure-defender.levels.level-01)

(defn -main
  [& args]
  (load-level)
  (create-gui)
  (redisplay gl/main-frame)
  (run))
