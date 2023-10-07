# Name:  Project One: Dialer
## Author: Geovani Gonzalez
## Class:  CS 478 Spring 23
## Date:   March 1st, 2023
This is the main activity file for Project One. Application takes number
from user and then checks if it is in a valid format. If so, it is given to the
dialer app of the phone's choice, and put in, without dialing.

Notes:
 1. The PDF requires use of the setOnEditorActionListener, as seen in the
SecondActivity file. This has been done, however I feel the need to point out,
this means the user's input is ONLY captured when they press enter. This seems
inferior to the version I first went with, where user input is captured with
the use of onBackPressed, so even if they don't press enter it is saved.
These two methods are of course incompatible, so the one in the PDF has been kept.

2. The editText field gives a numeric input keypad, but this prevents only the
use of alphabetic characters. You can still type in '(', ')', '-', etc.
