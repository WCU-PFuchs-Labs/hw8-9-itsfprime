[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-2e0aaae1b6195c2367325f4f02e2d04e9abb55f0b24a779b69b11b9e10269abc.svg)](https://classroom.github.com/online_ide?assignment_repo_id=21286404&assignment_repo_type=AssignmentRepo)
## CSC 240 Computer Science III

### Homework 8 & 9 - Generations

[![Points badge](../../blob/badges/.github/badges/points.svg)](../../actions)

**Checkpoint 1 (HW8)** *TestGeneraton* - Due one week from release.  
**Checkpoint 2 (HW9)** *TestGP* - Due two weeks from release.

The next step in the genetic programming project is to read and store a data file (such as the wine quality data set).  Each of the trees in a generation of trees will be tested against the selected data file, and its "fitness" thus measured.  Here is a simple example.
Suppose there is just a single *independent variable* x0.  A (very short) data file might look like this:

```
 y ,    x0
1.2 ,  1.0
4.1 ,  2.0
8.8 ,  3.0
```


Now suppose the tree is ((x0 + 0.25) * x0).  We want to find out how close the tree value, for each x0 value, is to the given y value in the data.  One standard way of doing this is to add up (over the rows of data) the square of the "deviation".  For this example, the result is

```
Fitness =     Math.pow((((1.0 + 0.25) * 1.0) – 1.2),2)
            + Math.pow((((2.0 + 0.25) * 2.0) – 4.1),2)
            + Math.pow((((3.0 + 0.25) * 3.0) – 8.8),2)
             = 1.065
```

The work you’ve done so far allows you to evaluate any tree on a `double[]`, an array of values for `x0`, `x1`,..., corresponding to a single data row.  The next step is to be able to evaluate over multiple rows (subtracting and squaring for each row, as above) and sum the results for each row.

To do this, use the two classes `DataRow` and `DataSet`, from the Linear Regression project. 


## GPTree

Once the `tabular.DataRow` and `tabular.DataSet` classes are working correctly, the next step is to modify the `GPTree` class to implement `Comparable<GPTree>` and `Cloneable` and to have the following methods:

1.  `public void evalFitness(DataSet dataSet)`- accepts a `DataSet` object as its argument.  Since you already have an `eval()` method that takes a `double[]`, it shouldn't be too hard to extract each `DataRow`'s array of `x` values and feed it to your existing method (code reuse at work).  The `GPTree` `eval()` method should run through each of the `DataRow`s, evaluate the tree, subtract the y value, and square the result, all the while keeping a running sum of the squared differences.  The final sum is the `GPTree`'s fitness value.
2. `public double getFitness()` - return the fitness computed after `evalFitness()` is called.
3. `public int compareTo(GPTree t)` - compare the fitness values and return -1 for less than, 1 for greater than, and 0 when the values are equal.
4. `public boolean equals(Object o)` - return `true` when `compareTo((GPTree) o)` is 0, and `false` otherwise. Make sure to check to see if the object is not null, and is a GPTree first, and if it's not a GPTree or it is null then return false.
5. `public Object clone()` - in addition to calling clone on super similar to `clone()` in Node, and then make sure to clone `root` since it is a Cloneable Object (or if you are using the Algebra implementation from HW8, then you can use the copy constructor to copy `root`).

## Generation 

The last steps are the creation of the `Generation` class. The `Generation` class should probably have the following constructor and methods:

1. `Generation(int size, int maxDepth, String fileName)` - creates a DataSet from using the `fileName`, then creates the factories and random number generator necessary to construct a `GPTree`. Then creates an array of `size` GPTrees each with a `maxDepth` maximum depth. 
2. `public void evalAll()` - This evaluates the current generation of `GPTrees` by evaluating the Fitness of each tree against the current `DataSet` and then sorts the array in place using `Arrays.sort()`
3. `public ArrayList<GPTree> getTopTen()` - this returns an ArrayList of the top 10 GPTrees. (i.e. the trees with the lowest fitness in increasing order of fitness.) (only works after evaluating all.)
4. `public void printBestFitness()` - prints the best fitness value (only works after evaluating all.)
5. `public void printBestTree()` - prints the best Tree (only works after evaluating all).
6. `public void evolve()` - (For Checkpoint 2) select 2 of the more fit trees at random, `clone()` each tree and then call crossover. Add these to the new array of children. Repeat (array size)/2 times until the new array has the same number of trees in the next generation.

## Checkpoint 1 - TestGeneration

As in the last homework, write a test class that demonstrates your stuff.  Call it `TestGeneration`.  Have this class’s main() method prompt the user for a data file. Then create a generation of 500 GPTrees.  Get the data into a DataSet object, and evaluate each GPTree.  Print out the GPTree with the smallest fitness.  After all, this is the tree that best fits the data. Then print the fitnesses of each of the top ten GPTrees and make sure that they are in increasing order. The output for the top ten fitnesses should start:

```
Top Ten Fitness Values: 
```

The values should have exactly 2 decimal places at the end, and they should be separated by commas.

Testing your program can be difficult because it depends on random numbers, and because of the number crunching involved.  My personal testing plan was to use a very small data file, a small number of trees, and small (but still randomly generated) trees, which are easy to produce by setting maxDepth to 2 or 3.

***Note:*** Since the wine data has so many independent variables, you may want to change the `addRandomKids()` method so that it is more likely to make a random operator. Otherwise there will be a lot of very short trees. To change this you can change step 2 of addRandomKids to get a random number between 0 and numOperators (do this for both the left child and the right child). If you are using the starter code change the following lines:

```java
            // right child
            int choice = rand.nextInt(f.numOps() + f.numIndep());
            if (choice < f.numIndep()) {
```

to

```java
            // right child
            int choice = rand.nextInt(f.numOps());
            if (choice < 1) {
```

and change

```java
            // left child
            choice = rand.nextInt(f.numOps() + f.numIndep());
            if (choice < f.numIndep()) {
```

to

```java
            // left child
            choice = rand.nextInt(f.numOps());
            if (choice < 1) {
```

*If you are not sure what to change, please ask.*



## Checkpoint 2 - TestGPTree

For this Checkpoint you'll need to have `evolve()` working. Write a test class called `TestGP` that prompts the user for a data file, and then creates a generation of 500 GPTrees and gets the fitness. Then loop for 50 generations printing the best tree and the best fitness for each generation. Look at the some of the best fit trees and see how similar or different they are. Note: The best fitness should be decreasing, possibly with a little bit of noise, over the course of the 50 generations.
In your output, you should begin the output of each generation with the word Generation and the generation number, so for generation 1 it should be:

```
Generation 1:
```

Test on the simple data set below as well as on the 2 wine data sets included.

Here is a simple dataset to try.

```
y,x0
0,0
0.19,0.1
0.36,0.2
0.51,0.3
0.64,0.4
0.75,0.5
0.84,0.6
0.91,0.7
0.96,0.8
0.99,0.9
1,1
0.99,1.1
0.96,1.2
0.91,1.3
0.84,1.4
0.75,1.5
0.64,1.6
0.51,1.7
0.36,1.8
0.19,1.9
0,2
-0.21,2.1
-0.44,2.2
-0.69,2.3
-0.96,2.4
-1.25,2.5
-1.56,2.6
-1.89,2.7
-2.24,2.8
-2.61,2.9
-3,3
```
 



