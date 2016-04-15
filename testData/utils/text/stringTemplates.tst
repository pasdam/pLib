# Test format:
#	<first_string>||||||||<second_string>||||||||<expected_template>
#
# Template's placeholder must be "*" (without quotes)

# basic tests
string||||||||string||||||||string
string||||||||string2||||||||string*
string||||||||string2X||||||||string*
string||||||||siring||||||||s*ring
string||||||||tring2||||||||*tring*
string1||||||||string||||||||string*
string1||||||||string2||||||||string*
string1||||||||string2X||||||||string*
string1||||||||siring||||||||s*ring*
string1||||||||tring2||||||||*tring*
string1X||||||||string||||||||string*
string1X||||||||string2||||||||string*
string1X||||||||string2X||||||||string*X
string1X||||||||siring||||||||s*ring*
string1X||||||||tring2||||||||*tring*
sring||||||||string||||||||s*ring
sring||||||||string2||||||||s*ring*
sring||||||||string2X||||||||s*ring*
sring||||||||siring||||||||s*ring
sring||||||||tring2||||||||*ring*
tring1||||||||string||||||||*tring*
tring1||||||||string2||||||||*tring*
tring1||||||||string2X||||||||*tring*
tring1||||||||siring||||||||*ring*
tring1||||||||tring2||||||||tring*

# regression tests (strings for which the method didn't work)
#https://www.amazon.it/s/ref=sr_nr_p_n_size_two_browse-_3/276-031||||||||https://www.amazon.it/s/ref=sr_nr_p_n_size_two_browse-_4/276-031||||||||https://www.amazon.it/s/ref=sr_nr_p_n_size_two_browse-_*/276-031
#https://www.amazon.it/s/ref=sr_nr_p_n_size_two_browse-_3/276-03||||||||https://www.amazon.it/s/ref=sr_nr_p_n_size_two_browse-_4/276-03||||||||https://www.amazon.it/s/ref=sr_nr_p_n_size_two_browse-_*/276-03

browse-_3/276-031||||||||browse-_4/276-031||||||||browse-_*/276-031

#https://www.amazon.it/s/ref=sr_nr_p_n_size_two_browse-_3/276-0314461-0713720?fst=as%3Aoff&amp;rh=n%3A412609031%2Cn%3A%21412610031%2Cn%3A435505031%2Cn%3A473535031%2Cp_89%3AOlympus%2Cp_n_size_two_browse-vebin%3A1605446031&amp;bbn=473535031&amp;ie=UTF8&amp;qid=1454262178&amp;rnid=1605296031||||||||https://www.amazon.it/s/ref=sr_nr_p_n_size_two_browse-_4/276-0314461-0713720?fst=as%3Aoff&amp;rh=n%3A412609031%2Cn%3A%21412610031%2Cn%3A435505031%2Cn%3A473535031%2Cp_89%3AOlympus%2Cp_n_size_two_browse-vebin%3A1605450031&amp;bbn=473535031&amp;ie=UTF8&amp;qid=1454262178&amp;rnid=1605296031||||||||https://www.amazon.it/s/ref=sr_nr_p_n_size_two_browse-_*/276-0314461-0713720?fst=as%3Aoff&amp;rh=n%3A412609031%2Cn%3A%21412610031%2Cn%3A435505031%2Cn%3A473535031%2Cp_89%3AOlympus%2Cp_n_size_two_browse-vebin%3A16054*031&amp;bbn=473535031&amp;ie=UTF8&amp;qid=1454262178&amp;rnid=1605296031