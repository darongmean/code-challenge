(defproject code-interview "0.1.0"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}

  :source-paths ["src/clj"]
  :java-source-paths ["src/java"]
  :javac-options ["-target" "11" "-source" "11"]

  :dependencies [[org.clojure/clojure "1.10.2"]
                 [expound "0.8.9"]]

  :repl-options {:init-ns code-interview.core}

  :profiles {:dev {:dependencies [[orchestra "2021.01.01-1"]
                                  [com.gfredericks/test.chuck "0.2.10"]]
                   :injections [(require '[clojure.spec.alpha :as s])
                                (require '[expound.alpha :as expound])
                                (require '[orchestra.spec.test :as st])
                                (set! s/*explain-out* expound/printer)
                                (s/check-asserts true)
                                (st/instrument)]}})
