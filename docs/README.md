# FluentLenium Site

This directory contains the code for the FluentLenium Site, [http://fluentlenium.io/](http://fluentlenium.io/).

## Running locally

Install Ruby 2.1.0 or higher: [https://www.ruby-lang.org/en/downloads/](https://www.ruby-lang.org/en/downloads/)

Install bundler `gem install bundler`

You can preview your contributions before opening a pull request by running from within the directory:

1. `bundle install`

If `bundle install` fails to resolve dependencies conflict please try again after `bundle update`

2. `bundle exec jekyll serve`

3. Go to [http://127.0.0.1:4000/](http://127.0.0.1:4000/)

If you have any problems please refer to official docs
[https://help.github.com/articles/setting-up-your-github-pages-site-locally-with-jekyll/#requirements](https://help.github.com/articles/setting-up-your-github-pages-site-locally-with-jekyll/#requirements)

### Windows

You need to comment out `therubyracer` gem from `Gemfile`.
