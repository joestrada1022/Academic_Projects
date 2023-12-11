#include <stdio.h>
#include <ctype.h>

int main(int argc, char **argv)
{
    FILE *fp;
    char ch; char str[25];
    int a=0, e=0, i=0, o=0, u=0, cons=0, punct=0, total=0;
    printf("Enter File Name: ");
    scanf("%s", str);
    fp = fopen(str, "r");
    while ((ch = getc(fp)) != EOF)
    {
        if (isalpha(ch))
        {
            total++;
            switch (ch)
            {
            case 'a': case 'A':
                a++;
                break;
            case 'e': case 'E':
                e++;
                break;
            case 'i': case 'I':
                i++;
                break;
            case 'o': case 'O':
                o++;
                break;
            case 'u': case 'U':
                u++;
                break;

            default:
                cons++; // assumes ch is a consonant
            }
        }
        else if (ispunct(ch))
        {
            total++;
            punct++;
        }
    }
    fclose(fp);
    fp = fopen("output.txt", "w");
    fprintf(fp, "Number of occurrences:\n");
    fprintf(fp, "a: %d, e: %d, i: %d, o: %d, u: %d, consonants: %d, punct: %d\n\n", a, e, i, o, u, cons, punct);
    fprintf(fp, "Percentages of various characters:\n");
    fprintf(fp, "a: %.2f%%, e: %.2f%%, i: %.2f%%, o: %.2f%%, u: %.2f%%, consonants: %.2f%%, punct: %.2f%%\n\n", (float)a / total * 100, (float)e / total * 100, (float)i / total * 100, (float)o / total * 100, (float)u / total * 100, (float)cons / total * 100, (float)punct / total * 100);
    fclose(fp);
    fp = fopen("output.txt", "r");
    while ((ch = getc(fp)) != EOF)
        printf("%c", ch);
    fclose(fp);
    return 0;
}