--QuickCheck biblioteca
-- >import Test.QuickCheck

-- functie l, lungime list
l [] = 1
l (h:t) = 1 + l t

-- functie t, lista de numere intregi 
-- t :: [Int] -> Bool
t a = (l a == length a)

tt l = (reverse (reverse l) == l)

-- converteste un numar in baza 2
--convert 13 -> [1,1,0,1]

--convert [] nr = 0
--convert (hd:tl) = convert (hd:tl) 1
--convert (hd:tl) nr =hd*nr + convert tl nr*2  

convert x | x < 0 = convert (-x)
convert 0 = []
--convert n | n `mod` 2 == 1 = convert (n `div` 2) ++ [1]
--          | n `mod` 2 == 0 = convert (n `div` 2) ++ [0]
convert n = convert (n `div` 2) ++ [n `mod` 2]

--convert' [] = 0
--convert' (hd:tl)	| hd == 1 = 2 ^ length tl + convert' tl
--			| otherwise  = convert' tl
--convert' (h:t)  = 
convert' l = convert'_aux (reverse l)
convert'_aux [] = 0
convert'_aux (h:t) = (convert'_aux t)*2 + h

ttt l = (convert (convert' l) == l)
-- de terminat acasa

-- tipul unit ()
data U = V --equ

--f::()->String
f () = "Stringus"

f' [] = ()
f' (x:ds) = () -- >- void

-- Tip : IO a
-- Valorile de tip "IO a" sunt actiuni care dupa ce sunt executate, efectuate, produs o valoare de tip a.

fst' :: (Int, String) -> Int -- (tipul perechiile de numere intregi si string-uri)
--(1,"Aba") :: (Int, String)

fst' (x,y) = x
snd' (x,y) = y
--snd' :: (a, b) -> b 

-- functie arg : cuplu nr, string -> string :: f (3, "Ana") -> anaanaana
fct :: (Int, String) -> String
fct (0, _) = ""
fct (n, s) = s ++ (fct (n-1) s)

-- 2 functii, tuplify (a->b->c) -> ((a,b) -> c) functie
	-- untuplify ((a,b) -> c) => (a -> b -> c)
	      