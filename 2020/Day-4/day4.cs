using System;
using System.Threading;
using System.Xml.Linq;
using System.IO;
using System.Collections;

namespace AdventOfCode
{
    class Day4
    {
        static void Main(string[] args)
        {
            ArrayList passportList = new ArrayList();

            using (StreamReader reader = new StreamReader("input.txt"))
            {
                string passport = "";
                string line;
                while ((line = reader.ReadLine()) != null)
                {
                    if (line != "")
                        passport += " " + (line.Replace("\n", " "));
                    else
                    {
                        passportList.Add(passport);
                        passport = "";
                    }       
                }
            }

            int validPassports = 0;

            for (int i = 0; i < passportList.Count; i++)
            {
                string[] passportFields = ((string) passportList[i]).Split(' ');

                PassportModel model = new PassportModel();

                foreach (string field in passportFields)
                {
                    string[] splitField = field.Split(':');

                    Console.WriteLine(field);
                    //Console.Write(splitField[1] + " ");

                    switch (splitField[0])
                    {
                        case "byr":
                            model.BirthYear = int.Parse(splitField[1]);
                            break;
                        case "iyr":
                            model.IssueYear = int.Parse(splitField[1]);
                            break;
                        case "eyr":
                            model.ExpirationYear = int.Parse(splitField[1]);
                            break;
                        case "hgt":
                            model.Height = splitField[1];
                            break;
                        case "hcl":
                            model.HairColor = splitField[1];
                            break;
                        case "ecl":
                            model.EyeColor = splitField[1];
                            break;
                        case "pid":
                            model.PassportID = splitField[1];
                            break;
                        case "cid":
                            model.CountryID = int.Parse(splitField[1]);
                            break;
                    }
                }

                if (model.isValidPassport())
                    validPassports++;

                Console.WriteLine("The number of valid passports is: " + validPassports);
            }
        }
    }

    class PassportModel
    {
        public static ArrayList EYE_COLOR_VALUES = new ArrayList() { "amb", "blu", "brn", "gry", "grn", "hzl", "oth" };

        public int BirthYear { get; set; }
        public int IssueYear { get; set; }
        public int ExpirationYear { get; set; }
        public string Height { get; set; }
        public string HairColor { get; set; }
        public string EyeColor { get; set; }
        public string PassportID { get; set; }
        public int CountryID { get; set; }

        public PassportModel()
        {
            BirthYear = -1;
            IssueYear = -1;
            ExpirationYear = -1;
            Height = "";
            HairColor = "";
            EyeColor = "";
            PassportID = "";
            CountryID = -1;
        }

        public bool areRequiredFieldsPresent()
        {
            return BirthYear != -1 && IssueYear != -1 && ExpirationYear != -1 && Height != "" && HairColor != "" && EyeColor != "" && PassportID != "";
        }

        public bool isValidPassport()
        {
            bool byrValid = BirthYear >= 1920 && BirthYear <= 2002;
            bool iyrValid = IssueYear >= 2010 && IssueYear <= 2020;
            bool eyrValid = ExpirationYear >= 2020 && ExpirationYear <= 2030;

            bool hgtValid = false;
            if (Height.Contains("cm"))
            {
                int num = 0;
                hgtValid = int.TryParse(Height.Replace("cm", ""), out num);
                if (num < 150 || num > 193)
                    hgtValid = false;
            }
            else if (Height.Contains("in"))
            {
                int num = 0;
                hgtValid = int.TryParse(Height.Replace("in", ""), out num);
                if (num < 59 || num > 76)
                    hgtValid = false;
            }

            bool hclValid = false;
            if (HairColor.IndexOf('#') == 0)
            {
                int num;
                hclValid = int.TryParse(HairColor.Remove(0, 1), System.Globalization.NumberStyles.HexNumber, null, out num);
            }

            bool eclValid = EYE_COLOR_VALUES.Contains(EyeColor);

            long useless;
            bool pidValid = (PassportID.Length == 9) && (long.TryParse(PassportID, out useless));

            return areRequiredFieldsPresent() && byrValid && iyrValid && eyrValid && hgtValid && hclValid && eclValid && pidValid;
        }
    }
}