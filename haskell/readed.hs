-- promt :: String -> IO String
promt s = do
		putStrLn s
		x <- getLine
		return x

main :: IO ()
main = do 
	x <- getLine 
	
	if x == "q" then
		return ()
	else
		do
			main
			putStrLn x

-- dupa ce citit liniile, citit in ordinea citita (Acasa)






