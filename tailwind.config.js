/** @type {import('tailwindcss').Config} */
module.exports = {
    presets: [require('./node_modules/@hephaistox/tailwind-config/tailwind.config.js')],
    // Code is not always present in cljs form in production
  // and whatever if it is, to make the minification happens we need to have a
  // look to only left classes in js
                  content:  process.env.NODE_ENV == 'production' ?
                  ['./resources/public/js/compiled/cljs-runtime/**/*.js',
                   './node_modules/tw-elements/js/**/*.js'] :
                  ['./src/**/*.{html,js,clj,cljs,cljc}',
                   './node_modules/tw-elements/js/**/*.js']}
