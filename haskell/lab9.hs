--foldr [] [1,2,3,4,5]
f a [] = a:[]
f a (hd:tl) = hd:( f a tl) 

--foldl f [] [1,2,3,4,5]
f' l x = x:l

--[1,1,0,1] -> 13 (nr in baza 10)

bzd (hd:tl) = bzd' (hd:tl) 1
bzd' [] nr = 0
bzd' (hd:tl) nr =hd*nr + bzd' tl nr*2  

binary_list [] = 0
binary_list (hd:tl)	| hd == 1 = 2 ^ length tl + binary_list tl
			| otherwise  = binary_list tl


f'' x y = x*2 + y
