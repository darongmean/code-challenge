(ns code-interview.linklists)


(defn slow-fast-pair
  "The \"runner\" (or second pointer) technique is used in many linked list problems. The runner technique
  means that you iterate through the linked list with two pointers simultaneously, with one ahead of the other.

  The \"fast\" node might be ahead by a fixed amount, or it might be hopping multiple nodes for each one node
  that the \"slow\" node iterates through.

  For example, suppose you had a linked list a1 - >a2 - > ••• - >an - >b1 - >b2 - > ••• - >bn and
  you wanted to rearrange it into a1 - >b1 - >a2 - >b2 - > •.• - >an - >bn•
  You do not know the length of the linked list (but you do know that the length is an even number).
  You could have one pointer pl (the fast pointer) move every two elements for everyone move that p2
  makes. When pl hits the end of the linked list, p2 will be at the midpoint. Then, move pl back to the front
  and begin \"weaving\" the elements. On each iteration, p2 selects an element and inserts it after pl. "
  [coll]
  (let [fast-runner (concat (take-nth 2 (drop 1 (seq coll)))
                            (repeat nil))
        slow-runner (seq coll)]
    (map vector slow-runner fast-runner)))


(defn verify-palindrome [{:keys [head tail]}]
  (every? true? (map = head tail)))


(defn collect-head-and-tail
  "Return a map with the following key:
  :head a collection of element from the start to the middle(exclude middle if the length is odd)
  :tail a collection of element from the end to the middle(include middle if the length is odd)"
  [coll]
  (let [conj-head-or-tail (fn [acc [x y]] (if (nil? y)
                                            (update acc :tail conj x)
                                            (update acc :head conj x)))]
    (reduce conj-head-or-tail {:head [] :tail (list)} coll)))


(defn palindrome? [coll]
  (-> coll
      (slow-fast-pair)
      (collect-head-and-tail)
      (verify-palindrome)))
