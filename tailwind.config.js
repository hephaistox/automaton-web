/** @type {import('tailwindcss').Config} */
module.exports = {
                  mode: 'jit',
  // Code is not always present in cljs form in production
  // and whatever if it is, to make the minification happens we need to have a
  // look to only left classes in js
                  content:  process.env.NODE_ENV == 'production' ?
                  ['./resources/public/js/compiled/cljs-runtime/**/*.js',
                   './node_modules/tw-elements/dist/js/**/*.js'] :
                  ['./src/**/*.{html,js,clj,cljs,cljc}',
                   '../automaton_web_dev/src/**/*.{html,js,clj,cljs,cljc}',
                   './node_modules/tw-elements/dist/js/**/*.js'],
                  theme: {
                          extend: {
                              animation: {
                                  'fade-in': '2s fadeIn',
                              },
                              keyframes: {
                                  fadeIn: {
                                      '0%, 99%': {visibility: 'hidden'},
                                      '100%': {visibility: 'visible'},
                                  }
                              },
                              colors: {
                                     'black': '#0F1720', // Our custom black
                                     'primary': {
                                       '50': '#fef4ee',
                                       '100': '#fce5d8',
                                       '200': '#f9c7af',
                                       '300': '#f4a17d',
                                       '400': '#ef6f48',
                                       '500': '#ec5834',
                                       '600': '#dc321a',
                                       '700': '#b62418',
                                       '800': '#911f1b',
                                       '900': '#751c19',
                                       '950': '#3f0b0b',
                                       DEFAULT: '#EC5834',
                                     },
                                     'secondary': '#F2974B', // Our secondary color - dark orange
                                     'additional': '#FF9E4F',
                                     'theme-dark': {DEFAULT: '#111827',
                                                    'primary':'#111827',
                                                    'secondary': '#4b5563'},
                                     'theme-light': {DEFAULT: '#f9fafb',
                                                     'primary':'#f9fafb',
                                                     'secondary': '#9ca3af'}}}},
                  plugins: [require('@tailwindcss/forms'),
                            require('tw-elements/dist/plugin.cjs')],

                  darkMode: 'class'
                  }
