-- Pirghie Dimitrie, InfoIasi 2015, PF - Project
-- 2048 Simple haskell implementation

import Prelude hiding (Left, Right)
import Data.Char (toLower)
import Data.List
import System.IO
import System.Random
import Text.Printf

data Move = Up | Down | Left | Right
type Table = [[Int]]


-- start table
startTable :: Table
{-
startTable =    [[4,4,4,0],
                 [16,2,4,8],
                 [4,4,8,0],
                 [64,32,16,8]]
                 -}
startTable =    [[0,0,0,0],
                 [0,0,0,0],
                 [0,0,0,2],
                 [0,0,0,2]]

transpose' :: [[Int]] -> [[Int]]
transpose' [[]]    = []
transpose' [[], _] = []
transpose' rows    = (map head' rows) : transpose' (map tail' rows)

  where
    head' (x:_) = x
    tail' (_:xs) = xs

sumRow :: [Int] -> [Int]
sumRow xs = mergeRow ++ paddingWithZeros
    where   paddingWithZeros      = replicate (length xs - length mergeRow) 0
            mergeRow              = combineRow $ filter (/=0) xs -- filter (/= 0) xs . combineRow

            combineRow (x:y:xs)  | x == y = x * 2 : combineRow xs
                                 | otherwise = x  : combineRow (y:xs)
            combineRow list = list

moveTable :: Table-> Move -> Table
moveTable gameTable Left    = map sumRow gameTable
moveTable gameTable Right   = map (reverse . sumRow . reverse) gameTable
--moveTable gameTable Down    = transpose ( map reverse ( map sumRow (transpose gameTable) ) )
--moveTable gameTable Up      = transpose (map sumRow (transpose gameTable))--map (transpose' . sumRow . transpose') gameTable
moveTable gameTable Up    = transpose $ moveTable (transpose gameTable) Left
moveTable gameTable Down  = transpose $ moveTable (transpose gameTable) Right


-- prints a row (list of int's)
printRow :: [Int] -> String
printRow (x:xs) = padding x ++ show x ++ printRow xs
    where padding x = concat $ replicate (10 - length (show x) ) " "
printRow [] = ""

-- prints a Table
printTable :: Table -> IO ()
printTable table = do
                        mapM_ (putStrLn . printRow) table

checkWin :: Table -> Bool
checkWin gameTable = [] /= filter (==2048) (concat gameTable)

areMoves :: Table -> Move-> Bool
areMoves gameTable Left  = if gameTable /= (moveTable gameTable Left) then True
                           else areMoves gameTable Right
areMoves gameTable Right = if gameTable /= (moveTable gameTable Right) then True
                           else areMoves gameTable Up
areMoves gameTable Up    = if gameTable /= (moveTable gameTable Up) then True
                           else areMoves gameTable Down
areMoves gameTable Down  = if gameTable /= (moveTable gameTable Up) then True
                           else False

captureMove :: IO Move
captureMove = do
    inp <- getLine
    case inp of
        "w" -> return Up
        "a" -> return Left
        "s" -> return Down
        "d" -> return Right
        _   -> do putStrLn "wasd !"
                  captureMove


nextTable :: Table -> IO Table
nextTable grid = do m <- captureMove
                    let newTable = moveTable grid m
                    return newTable


gameLoop :: Table -> IO ()
gameLoop gameTable =
                case areMoves gameTable Left of
                    True -> do  printTable gameTable
                                if checkWin gameTable
                                then putStrLn "You Won"
                                else do
                                       new_gameTable <- nextTable gameTable
                                       if gameTable /= new_gameTable
                                       then do new_gameTable <- addNumber new_gameTable
                                        --       putStrLn "Added number"
                                        --       print new_gameTable
                                               gameLoop new_gameTable
                                       else do putStrLn "No added"
                                               gameLoop new_gameTable
                    False -> do     printTable gameTable
                                    putStrLn "Game Over No "

getEmptyCoords :: [[Int]] -> [(Int, Int)]
getEmptyCoords gameTable = filter (\(r,c) -> (gameTable!!r)!!c == 0 ) (genCoords 0 3)

genRCordsFromZero :: (Int, Int) -> Int-> [(Int, Int)]
genRCordsFromZero (a, b) max | b <= max = [(a, b)] ++ genRCordsFromZero (a, b+1) max
                             | True = []

-- Example genCoords 0 1 -> "[(0,0),(0,1),(1,0),(1,1)]"
genCoords :: Int -> Int -> [(Int, Int)]
genCoords a m | a < 0 = []
              | a <= m = genRCordsFromZero (a, 0) m ++ (genCoords (a+1) m)
              | True = []


setValInTable :: Table -> (Int, Int) -> Int -> Table
setValInTable gameTable (r,c) nw = beforeL ++ [middle] ++ afterL
                                   where    beforeL = take r gameTable
                                            middle = take c (gameTable!!r) ++ [nw] ++ drop (c+1) (gameTable!!r)
                                            afterL  = drop (r+1) gameTable

{-
setValInTable grid (row,col) nw  = pre ++ [mid] ++ post
    where pre  = take row grid
          mid  = take col (grid!!row) ++ [nw] ++ drop (col + 1) (grid!!row)
          post = drop (row + 1) grid
-}


addNumber :: Table -> IO Table
addNumber gameTable = do    generator <-newStdGen
                            let emptyCoords = getEmptyCoords gameTable
                                position = (head (randoms generator :: [Int])) `mod` (length emptyCoords)
                                coordChossen = emptyCoords!!position
                                value = [4,2,2,2,2,2,2,2,2,2]!!(head (randoms generator :: [Int]) `mod` 10)
                                newGameTable = setValInTable gameTable coordChossen value
                          --  print coordChossen
                            return newGameTable

main :: IO ()
main = do gameLoop startTable

        --print (setValInTable startTable (0,0) 4)
        --print (setValInTable startTable (1,1) 4)
        --print (setValInTable startTable (1,2) 4)
        --print (setValInTable startTable (1,3) 4)
        --print (setValInTable startTable (1,5) 4)

    --print startTable
    --print (show (genCoords 0 1))
    --print (show (getEmptyCoords startTable))
    --g <- newStdGen
    --pos             = head (randoms g :: [Int]) `mod` length candidates
    --print (show ( (head (randoms g :: [Int])) `mod` length (getEmptyCoords startTable)))
    --print (setValInTable startTable (1,1) 0)
    --gameLoop startTable
    --print (sumRow [[1024,0,512,512,2],[4,4,0]])
    --printTable startTable
    {-
    print "--------------------"
    printTable (moveTable startTable Right)
    print "--------------------"
    -}
    --printTable (moveTable startTable Left)
    {-print "--------------------"
    printTable (moveTable startTable Up)
    print "--------------------"
    printTable (moveTable startTable Down)
    print "--------------------"
    print (areMoves startTable Left)
    -}
