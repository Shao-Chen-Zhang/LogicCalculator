# Shawn Zhang's Personal Project

## Logic Calculator


**What will the application do?**  
This application will be able to calculate the values produced by
formal logical sentences and output their truth tables using the 
logical connectives:  

- negation (~)
- conjunction (&)
- disjunction (v)
- conditional (>)
- biconditional (=)  

For example, "A & B" will produce a truth table (1=true, 0=false):  
A B | A & B  
1 1 | 1  
1 0 | 0  
0 1 | 0  
0 0 | 0  

Additional Functionality That May Be Added:  

- Understanding order of operations using parentheses 
[ ex. A v (B > (C & D)) ]
- Determining validity of an argument (in its formal 
form using logical connectives) from its premises and conclusion
- Drawing a circuit from a formal logical sentence using (not, and, or)
gates
- Translating informal logic sentences (English) to formal logic 
(ex. A & B)

Some of these I can see being pretty challenging to implement, so I 
probably won't get to all of them.  However, they all build upon the
basic code that will be used to produce the truth tables.



**Who will use it?**  
Anyone can use this program, however, it will mainly be used by those
with basic knowledge on logic, and logical sentences.  Most of this is 
covered in CPSC 121 so anyone with that course will know exactly how logic
works and what my program is suppose to do.  The main person using this 
program will most likely be me, though.  I am writing this program so that
I won't have to write out truth tables by hand for my CPSC 121 and PHIL 220
calculations.

**Why is this project of interest to you?**  
This project interests me because of its relevance to two courses
I'm taking right now: CPSC 121 and PHIL 220.  Throughout programming
this project I hope to gain a better understanding for the material 
covered in CPSC 121 and PHIL 220.  This project will also be of 
actual use to me in those courses.  I also find this project
interesting because there is a lot of different functionality that
can be added, so there's plenty of opportunity to try
implementing the more challenging functions.


## User Stories
- As a user, I want to be able to add a number of variables to my logic statement
- As a user, I want to be able to use the results of a logical connective as a variable
- As a user, I want to be able to see the results of all the logical connectives so far
- As a user, I want to be able to see how many variable I can use so far
- As a user, I want to be able to save the variables in my logic calculator to file
- As a user, I want to be able to load the variables in my logic calculator from file
- As a user, I want to be able to print all the variables in use

## Phase 4: Task 2

 **Exception Handling**
- Class: Variable
    - Methods: 
        - setVariable throws ImpossibleVariableSetupException
        - getTruthValsString throws EmptyListException
        - getTruthVals throws EmptyListException
        - addTruthVal throws InvalidTruthValueException
        - setTruthVals throws EmptyListException, InvalidTruthValueException
   
   
## Phase 4: Task 3
**UML and Refactoring**

No major refactoring necessary with what's included in the UML diagram.  However, I think some major refactoring 
is necessary with Exception Handling.  Many of the exceptions should be caught at a lower level.  Many of the exceptions
in Variable are actually unnecessary because they will never be thrown since those exception scenarios will not occur 
when they are called from at a higher level.  Instead, many of the exceptions thrown in Variable should be refactored so
that they are initially thrown in Variables.