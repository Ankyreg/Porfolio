## Parsing bank statements CSV 
A simple console program for the fictitious need of a bank to read and analyze the CSV of a statement of all transactions.
The functionality is to:
1. Read the document and create a table of summarized costs for specific accounts. So that you can see the amount of money that was debited to a specific bank account for the entire period. If any line does not correspond to the expected format, the user can choose two options to continue working:
- stop parsing completely;
- continue by skipping the unreadable position.
2. Save the generated cost table in a text document or in the default folder, or the user can enter a directory that is convenient for him.
3. The program can find and copy all CSV files in the specified directory and subdirectory
4. Separately display the total amount charged to the account and debited

Designed to demonstrate work skills:
- with files and directories, FileVisitor.
- CSV format
- testing methods with user input and output to the console (Mokito)