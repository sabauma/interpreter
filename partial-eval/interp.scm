
(define-primitive cdr - pure)
(define-primitive car - pure)
(define-primitive cons - pure)
(define-primitive apply - apply)

(define (lookup-env v senv)
  (cond
    ((null? senv)
     (lambda (denv) (error "empty environment" 'denv)))
    ((eq? v (car senv))
     (lambda (denv) (car denv)))
    (else
     (let ((rec (lookup-env v (cdr senv))))
       (lambda (denv)
         (rec (cdr denv)))))))

(define (interp exp senv)

  (define (constant? e)
    (or (boolean? e) (number? e)))

  (define (interp-many exp*)
    (if (null? exp*) (lambda (denv) '())
      (let ((l (interp (car exp*) senv))
            (r (interp-many (cdr exp*))))
        (lambda (denv)
          (cons (l denv) (r denv))))))

    (cond
      ((constant? exp) (lambda (denv) exp))
      ((symbol? exp)   (lookup-env exp senv))
      ((pair? exp)
       (cond
         ((eq? (car exp) 'lambda)
          (let ((body (interp (caddr exp) (cons (caadr exp) senv))))
            (lambda (denv)
              (lambda (y)
                (body (cons y denv))))))
         ((eq? (car exp) 'if)
          (let ((i (interp (cadr   exp) senv))
                (t (interp (caddr  exp) senv))
                (e (interp (cadddr exp) senv)))
            (lambda (denv)
              (if (i denv) (t denv) (e denv)))))
         (else
           (let ((rator (interp (car exp) senv))
                 (rands (interp-many (cdr exp))))
             (lambda (denv)
               (apply (rator denv) (rands denv)))))))))

(define evaluate
  (lambda (exp) ((interp exp '()) '())))

