#lang racket
(define (sphere_volume r)
(* r (* 1.333 (* 3.14 (* r r)) )))
(define (shell_volume r1 r2)
(- ( sphere_volume r2) (sphere_volume r1 ) ))

(define (close num1 num2 limit)
   (<= (abs (- num1 num2)) limit))
   
 (define (how-many a b c) 
(if ( = a 0)
(write " Equation is degenerate ")
(if (> (* b b) (* 4 (* a c) ) ) 
(writeln " 2 solutions")
(if (= (* b b) (* 4 (* a c) )) 
(writeln " 1 solution ")
(writeln " no solution") 
))))

(define (filter-out-symbol ls symbol)
(filter (lambda (x) (not (equal? x symbol))) ls))

;; did not do number 5

(define ((incnth a) n) 
(+ a n))
   