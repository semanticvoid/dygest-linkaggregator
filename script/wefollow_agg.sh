#!/usr/bin/sh

# Script to fetch the top 'Tech' users from wefollow and then fetch their
# corresponding twitter feeds

URL='http://wefollow.com/twitter/tech'
pg=1

# make a temp dir to store the fetched html pages
mkdir -p ~/temp

# fetch all the wefollow pages
for(( i = 1 ; i < 2 ; i++ ))
do
	req_url=$URL
	if [ $i -gt 1 ]
	then
		req_url="$URL/page$i"
	fi
	
	wget $req_url -O ~/temp/$i.html
done

# extract all the twitter usernames from the downloaded html
cat ~/temp/*.html | grep 'class="fn url"' | awk -F"http://twitter.com/" '{ print $2; }' | awk -F'"' '{ print $1; }' | sort | uniq >> ~/temp/users

# remove duplicates from a previous run
sort ~/temp/users | uniq > ~/temp/uniq_users && mv ~/temp/uniq_users ~/temp/users

# iterate over the usernames collected above and fetch their rss feed urls
for user in `cat ~/temp/users`
do
	wget "http://twitter.com/$user" -O ~/temp/user_page.html
	if [ $? -eq 0 ]
	then
		rss_url=`grep "http://twitter.com/statuses/user_timeline" ~/temp/user_page.html | awk -F"href=\"" '{ print $2 }' | awk -F'"' '{ print $1 }'`
		echo "$user\t$rss_url" >> ~/temp/user_rss
	fi
done

# eliminate duplicate (user, rss) tuples
sort ~/temp/user_rss | uniq > ~/temp/user_rss_temp && mv ~/temp/user_rss_temp ~/temp/user_rss	
