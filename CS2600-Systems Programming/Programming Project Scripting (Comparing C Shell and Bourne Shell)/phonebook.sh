#!/bin/sh

#function that checks if mobile # is a duplicate
function checkDupe {
    result=$( cut -d: -f3 $fixedfile ) #gets mobile number field from file
    result=$( echo "$result" | grep "$1" ) #compares $result to number inputted
    if [ "$result" != "" ];then #if there is a match, that means it is a duplicate
        echo -e "\nDuplicates not allowed"
        exit
        fi
}

#checks formatting of some fields
function checkFields {
    currentFile=$1
    zip=$( cat $currentFile | cut -d: -f4 | rev | cut -d' ' -f1 | rev | sed '/^[0-9]\{5\}$/d' ) # gets zip from file
    if [ "$zip" != "" ];then # tells the user of format error if incorrect
        echo "Zip code must 5 numbers in the format XXXXX"
        exit
    fi
    phoneNum=$( cut -d':' -f2,3 "$currentFile" ) # gets fields containing both home phone and mobile phone numbers
    phoneNum=$( echo "$phoneNum" | sed '/^[0-9]\{3\}-[0-9]\{3\}-[0-9]\{4\}:[0-9]\{3\}-[0-9]\{3\}-[0-9]\{4\}$/d' )
    if [ "$phoneNum" != "" ];then #tells the user if format is incorrect
        echo "Phone number must be in the format XXX-XXX-XXXX"
        exit
    fi
    name=$( cut -d':' -f1 "$currentFile" ) # gets name (both first and last)
    name=$( echo "$name" | sed '/^[a-zA-Z]\{1,\} [a-zA-Z]\{1,\}$/d' )
    if [ "$name" != "" ];then #checks it name is formatted correctly
        echo "Name must be in the format First Last"
        exit
    fi
    #checks formatting for birthday
    birth=$( cat "$currentFile" | rev | cut -d: -f2 | rev | grep -v '^[0-9]\{1,2\}/[0-9]\{1,2\}/[0-9]\{2,4\}$' )
    if [ "$birth" != "" ];then
        echo "Birth date must be in format: xx-xx-xx or xx-xx-xxxx"
        exit
    fi
}

#function that sorts by either first or last name depending on choice
function nameSort {
    if [ $1 -eq 1 ];then #sorts by first
        sort -t' ' -k1 $fixedfile
        temp=$( sort -t' ' -k1 $fixedfile ) #saves sorted into variable
        echo "$temp" > $fixedfile #sends contents of variable into temp file
        sorttype="k1" #sets global variable 'sorttype' to command to sort by first name
    elif [ $1 -eq 2 ];then #sorts by last
        sort -t' ' -k2 $fixedfile
        temp=$( sort -t' ' -k2 $fixedfile ) #saves sorted into variable
        echo "$temp" > $fixedfile #sends contents of variable into temp file
        sorttype="k2" #keeps record of last sorting
    else
        echo "Error, invalid input"
    fi
}

#function that reversely sorts by either first or last name
function reverseNameSort {
    if [ $1 -eq 1 ];then #reversely sorts by first
        sort -t' ' -rk1 $fixedfile
        temp=$( sort -t' ' -rk1 $fixedfile )
        echo "$temp" > $fixedfile #sends sorted to temp file
        sorttype="rk1" #keeps record of last sorting
    elif [ $1 -eq 2 ];then #reversely sorts by last
        sort -t' ' -rk2 $fixedfile
        temp=$( sort -t' ' -rk2 $fixedfile )
        echo "$temp" > $fixedfile
        sorttype="rk2"
    else
        echo "Error, invalid input"
    fi
}

#function that searches by either month or year
function dateSearch {
    if [ $1 -le 12 ];then #if user choice is less than 12, that means its a search by month
        grep ":$choice/" $fixedfile
    else #if user choice is greater, it searches for year
        grep "/$choice:" $fixedfile
    fi
}

#deletes by last name or mobile number
function lastNumberSearch {
    value=$( echo "$1" | sed 's/-//g' )
    if [[ $value =~ ^[0-9]+$ ]]; then # checks if the input is a number
        grep -v "[0-9]:$choice:" $fixedfile # removes the line by mobile number search
        temp=$( grep -v "[0-9]:$choice:" $fixedfile )
        echo "$temp" > $fixedfile #saves changes to temp file
    else
        grep -v " $choice" $fixedfile # removes the line by last name search
        temp=$( grep -v " $choice" $fixedfile )
        echo "$temp" > $fixedfile #saves changes to temp file
    fi
}

#function to add entry to phonebook. prompts user field by field
function insertion {
    echo -n "Enter first and last name: "
              read choice
              entry="$choice"
              echo -n "Enter home phone number: "
              read choice
              entry="$entry:$choice"
              echo -n "Enter (unique) mobile phone number: "
              read choice
              checkDupe $choice
              entry="$entry:$choice"
              echo -n "Enter address: "
              read choice
              entry="$entry:$choice"
              echo -n "Enter birthdate: "
              read choice
              entry="$entry:$choice"
              echo -n "Enter salary: "
              read choice
              entry="$entry:$choice"
              echo "$entry" >> $fixedfile; #adds entry to file
              checkFields $fixedfile #checks formatting
              if [ "$sorttype" != "" ];then #sorts new entry to previous sort command. if no sort command was previously used, it adds to end of file
                  temp=$( sort -t' ' "-$sorttype" $fixedfile )
              else temp=$( grep '' $fixedfile );fi
              echo "$temp"
              echo "$temp" > $fixedfile #saves changes to temp file
}

#start of main() script

#checks number of arguments and if file exists
#also initializes variables such as temp file that everything will be written to
if [ "$#" != 1 ];then
    echo "Script requires one argument"
    exit
elif [ -f "$1" ];then # if statement checks if the argument is a file
    file=$1 # saves argument name into variable
    fixedfile="temp.txt"
    #instead of giving error on detecting duplicate mobile numbers, (they must be unique) it just removes them with this awk line
    awk -F: '{ if (a[$3]++ == 0) print $0; }' "$@" $file > $fixedfile #removes lines with duplicate mobile numbers
    #rest of code works on variable without duplicates
else
    echo "Sorry, file $1 doesn't exist" # prints if argument doesn't exist
    exit #exits script
fi

#checks formatting of file
checkFields $fixedfile

#menu
while true;do
     echo "Listing of records in alphabetical order (enter a or A)

Listing of records in reverse alphabetical order (enter r or R)

Search for a record by Last Name (enter s or S)

Search for a record by birthday in a given year or month (enter b or B)

Insert record field by field (enter i or I)

Delete record from list by last name or phone number (enter d or D)

Exit (select e or E)"
     read response
     case $response in
         a|A) echo -n "By first name or last name? (enter 1 or 2): "
              read choice #gets user choice. if 1, it sorts by first name, if 2 sorts by last name
              nameSort $choice #calls sorting function
              echo "" ;;

         r|R) echo -n "By first name or last name? (enter 1 or 2): "
              read choice #gets user choice. first if 1, last if 2
              reverseNameSort $choice #calls reverse sorting function
              echo "";;

         s|S) echo -n "Enter last name: "
              read choice #gets user choice. searches by last name
              grep -i "$choice" $fixedfile # no need for a function since grep already does the work
              echo "";;

         b|B) echo -n "Enter a month or year to search from: "
              read choice
              dateSearch $choice #calls sarch by date function
              echo "";;
         d|D) echo -n "Enter a last name or phone number: "
              read choice
              lastNumberSearch $choice #calls search by last name or mobile number function
              echo "";;
         i|I) insertion;; #calls function to insert new entry
         e|E) echo "exiting...";
              echo -n "Would you like to save changes (y/n): "; read choice #asks if user want to save changes
              if [ "$choice" == "y" ];then #saves changes
                  temp=$( grep '' $fixedfile ) #saves temp file into variable
                  echo "$temp" > $file #saves changes back to original file that was inputted
              fi
              rm temp.txt #deletes temp file
              exit;;
         *) echo "Incorrect Choice. Try again"
            continue;;
     esac

done
