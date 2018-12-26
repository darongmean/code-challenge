(defproject code-interview "0.1.0"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}

  :dependencies [[org.clojure/clojure "1.9.0"]
                 [expound "0.7.2"]]

  :repl-options {:init-ns code-interview.core}

  :profiles {:dev {:dependencies [[orchestra "2018.12.06-2"]
                                  [com.gfredericks/test.chuck "0.2.9"]
                                  [org.clojure/core.rrb-vector "0.0.13"]]
                   :plugins      [[com.jakemccrary/lein-test-refresh "0.23.0"]
                                  [org.clojure/core.rrb-vector "0.0.13"]
                                  [venantius/ultra "0.5.2"
                                   :exclusions [org.clojure/clojure
                                                org.clojure/core.rrb-vector]]]}})
