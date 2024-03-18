#1. merged the GameResult and Result class into one -> to make code more efficient and get rid of redundancy

#2. merged ShipType class and Type class into one - > to make code more efficient and get rid of redundancy

#3. tweaked AIPlayer's takeShots method to debug -> had a bug in the PA03 implementation and needed to get rid of it

#4. got rid of all unnecessary System.out.println lines -> had a couple extra lines in PA03 implementation and got rid
of them for cleaner code

#5. changed AIPlayer's reportDamage method -> the PA03 implementation was mutating the AI's board which caused bugs. had
to change the code to debug.