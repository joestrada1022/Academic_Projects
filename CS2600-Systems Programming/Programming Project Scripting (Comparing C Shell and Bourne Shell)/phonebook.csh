#!/bin/csh

#checks number of arguments and if file exists
if ( "$#argv" != 1 ) then
    echo "Script requires one argument"
    exit
else if( -f "$1" ) then #chekcs if file exists
    set file = $1 #saves argument name into variable
    set fixedfile = "temp.txt" #saves name of temp file into variable
    #removes lines with duplicate mobile numbers (they must be unique)
    awk -F':' '{ if (a[$3]++ == 0) print $0;} ' $file > $fixedfile
else
    echo "Sorry, file $1 doesn't exist" #prints if argument doesn't exist
    exit #exits script
endif #rest of script works on updated temp file. this is so that the original file remains unchanged if the user doesnt want to save changes

#gets zip from user
cut -d: -f4 $fixedfile | rev | cut -d' ' -f1 | rev | sed '/^[0-9]\{5\}$/d' > "delete.txt"
if(`cat "delete.txt"` != "") then #tells the user of format error if there is one
    echo "Zip code must be 5 numbers in the format XXXXX"
    exit
endif

#gets fields containing both home and mobile phone numbers
cut -d: -f2,3 $fixedfile | sed '/^[0-9]\{3\}-[0-9]\{3\}-[0-9]\{4\}:[0-9]\{3\}-[0-9]\{3\}-[0-9]\{4\}$/d' > "delete.txt"
if ( `cat "delete.txt"` != "" ) then #tells the user if the format is incorrect
    echo "Phone number must be in the format XXX-XXX-XXXX"
    exit
    endif
cut -d: -f1 $fixedfile | sed '/^[a-zA-Z]\{1,\} [a-zA-Z]\{1,\}$/d' > "delete.txt" #gets name (both first and last)
if (`cat "delete.txt"` != "" ) then #checks if name is formatted correctly
    echo "Name must be in the format: First Last"
    exit
    endif
#checks formatting for birthday
cat "$fixedfile" | rev | cut -d: -f2 | rev | grep -v '^[0-9]\{1,2\}/[0-9]\{1,2\}/[0-9]\{2,4\}$' > "delete.txt"
if ( `cat "delete.txt"` != "" ) then
    echo "Birth date must be in format: x-x-xx or xx-xx-xxxx"
    exit
    endif

#menu
#sets default sorttype value. will give error if wasn't here. (didnt need to do this in bourne shell)
set sorttype = ""
while ( 1 )
    echo "Listing of records in alphabetical order (enter a or A)\n"

echo "Listing of records in reverse alphabetical order (enter r or R)\n"

echo "Search for a record by Last Name (enter s or S)\n"

echo "Search for a record by birthday in a given year or month (enter b or B)\n"

echo "Insert record field by field (enter i or I)\n"

echo "Delete record from list by last name or phone number (enter d or D)\n"

echo "Exit (select e or E)"

set response = $<
switch("$response")
case [Aa]:
    echo -n "By first name or last name? (enter 1 or 2): "
    set choice = $<
    if( "$choice" == 1 ) then
        sort -t' ' -k1 $fixedfile #sorts by first
        sort -t' ' -k1 $fixedfile > "delete.txt" #saves sorting to temp file
        cat "delete.txt" > "$fixedfile" #sends it back to file that was being worked on
        set sorttype = "k1" #records last sorting
    else if( "$choice" == 2 ) then
        sort -t' ' -k2 $fixedfile #sorts by first
        sort -t' ' -k2 $fixedfile > "delete.txt" #saves sorting to temp file
        cat "delete.txt" > "$fixedfile" #sends it back to file that was being worked on
        set sorttype = "k2" #keeps record of last sorting
    else
        echo "Error, invalid input"
    endif
        echo ""
    breaksw
case [Rr]:
        echo -n "By first name or last name? (enter 1 or 2): "
        set choice = $<
        if( "$choice" == 1 ) then #reversely sorts by first
        sort -t' ' -rk1 $fixedfile
        sort -t' ' -rk1 $fixedfile > "delete.txt"
        cat "delete.txt" > "$fixedfile" #sends sorted to temp file
        set sorttype = "rk1"
        else if($choice == 2) then
        sort -t' ' -rk2 $fixedfile #reversely sorts by last
        sort -t' ' -rk2 $fixedfile > "delete.txt"
        cat "delete.txt" > "$fixedfile"
        set sorttype = "rk2"
        else
        echo "Error, invalid input"
        endif
        echo ""
        breaksw
case [Ss]:
        echo -n "Enter last name: "
        set choice = $< #gets last name from user
        grep -i "$choice" $fixedfile
        echo ""
        breaksw
        case [Bb]:
        echo -n "Enter a month or year to search from: "
        set choice = "$<"
        if( $choice <= 12 ) then
        grep "\:$choice/" "$fixedfile" #searches by month
        else
        grep "/$choice\:" "$fixedfile" #searches by year
        endif
        echo ""
        breaksw
        case [Dd]:
        echo -n "Enter a last name or phone number: "
        set choice = $<
        set value = ` echo "$choice" | sed 's/-//g' ` #didnt send it to "delete.txt" here because its only one line so there is no formatting
        if( `expr $value : '[0-9]*$'` > 0 ) then # checks if its an int or a string
        grep -v "[0-9]\:$choice\:" $fixedfile # is an int, so it checks phone number format
        grep -v "[0-9]\:$choice\:" $fixedfile > "delete.txt"
        cat "delete.txt" > $fixedfile
        else
        grep -v " $choice" $fixedfile # is a string, so it checks to see if its a last name
        grep -v " $choice" $fixedfile > "delete.txt"
        cat "delete.txt" > $fixedfile
        endif
        echo ""
        breaksw
case [Ii]: #inserts field by field
    echo -n "Enter first and last name: "
        set choice = "$<"
        set entry = "$choice"
        echo -n "Enter home phone number: "
        set choice = "$<"
        set entry = "${entry}:$choice" #appends to entry
        echo -n "Enter (unique) mobile phone number: "
        set choice = "$<"
        cut -d: -f3 $fixedfile | grep "$choice" > "delete.txt" #checks if mobile number is unique
        if ( `cat "delete.txt"` != "" ) then
        echo "Mobile phone number must be unique"
        exit
        endif
        set entry = "${entry}:$choice" #appends to entry
        echo -n "Enter Address: "
        set choice = "$<"
        set entry = "${entry}:$choice" #appends to entry
        echo -n "Enter birthdate: "
        set choice = "$<"
        set entry = "${entry}:$choice" #appends to entry
        echo -n "Enter salary: "
        set choice = "$<"
        set entry = "${entry}:$choice" #appends to entry
        echo "$entry" >> $fixedfile #adds entry to file
        #chcks formatting of file now that entry was added
       #gets zip from user
cut -d: -f4 $fixedfile | rev | cut -d' ' -f1 | rev | sed '/^[0-9]\{5\}$/d' > "delete.txt"
if(`cat "delete.txt"` != "") then #tells the user of format error if there is one
    echo "Zip code must be 5 numbers in the format XXXXX"
    exit
endif

#gets fields containing both home and mobile phone numbers
cut -d: -f2,3 $fixedfile | sed '/^[0-9]\{3\}-[0-9]\{3\}-[0-9]\{4\}:[0-9]\{3\}-[0-9]\{3\}-[0-9]\{4\}$/d' > "delete.txt"
if ( `cat "delete.txt"` != "" ) then #tells the user if the format is incorrect
    echo "Phone number must be in the format XXX-XXX-XXXX"
    exit
    endif
cut -d: -f1 $fixedfile | sed '/^[a-zA-Z]\{1,\} [a-zA-Z]\{1,\}$/d' > "delete.txt" #gets name (both first and last)
if (`cat "delete.txt"` != "" ) then #checks if name is formatted correctly
    echo "Name must be in the format: First Last"
    exit
    endif
#checks formatting for birthday
cat "$fixedfile" | rev | cut -d: -f2 | rev | grep -v '^[0-9]\{1,2\}/[0-9]\{1,2\}/[0-9]\{2,4\}$' > "delete.txt"
if ( `cat "delete.txt"` != "" ) then
    echo "Birth date must be in format: x-x-xx or xx-xx-xxxx"
    exit
    endif
    if ( "$sorttype" != "" ) then #sorts the file if it was sorted before. if not, new entry is kept at end fo file
        sort -t' ' -$sorttype $fixedfile > "delete.txt"
    else grep '' $fixedfile > "delete.txt";endif
    cat "delete.txt"
    cat "delete.txt" > $fixedfile
    breaksw
        case [Ee]:
        echo "exiting..."
        echo -n "Would you like to save changes (y/n): "; set choice = $< #asks user if changes should be saved or not
        if ( "$choice" == "y" ) then
            grep '' $fixedfile > "delete.txt" #sends tempfile "Fixedfile" to "delete.txt"
            cat "delete.txt" > $file #sends back to original file that was inputted as an argument
            echo "Changes saved"
        endif
        rm temp.txt; rm delete.txt #deletes temp files
        exit
        breaksw
        default:
        echo "Incorrect Choice. Try again";continue
        breaksw
        endsw
end
