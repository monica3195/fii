-- promt :: String -> IO String
promt s = do
		putStrLn s
		x <- getLine
		return x

main :: IO ()
main = do 
	x <- promt "Name : ?"
	putStrLn ("Hello " ++ x)
	
	if x == "Stefan" then
		putStrLn "Welcome"
	else
		do
			putStrLn "Wrong name ?"
			main
