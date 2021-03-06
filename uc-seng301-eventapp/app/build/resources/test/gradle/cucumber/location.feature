#
# Created on Wed Apr 07 2021
#
# The Unlicense
# This is free and unencumbered software released into the public domain.
#
# Anyone is free to copy, modify, publish, use, compile, sell, or distribute
# this software, either in source code form or as a compiled binary, for any
# purpose, commercial or non-commercial, and by any means.
#
# In jurisdictions that recognize copyright laws, the author or authors of this
# software dedicate any and all copyright interest in the software to the public
# domain. We make this dedication for the benefit of the public at large and to
# the detriment of our heirs and successors. We intend this dedication to be an
# overt act of relinquishment in perpetuity of all present and future rights to
# this software under copyright law.
#
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
# AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
# ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
# WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
#
# For more information, please refer to <https://unlicense.org>
#

Feature: U2 - Add location to events from external API

  Scenario: AC1 - Add location to existing event consisting of a simple string
    Given There is an event with name "SENG301 Asg 3", description "Let's learn some patterns", type "assignment" and date "07/06/2021"
    When I add a location with name "Christchurch"
    Then The event has a location that is persisted when saving the event

  Scenario: AC2 - Retrieve location description and coordinates from external API
    Given There is an event with name "SENG301 Asg 3", description "Let's learn some patterns", type "assignment" and date "07/06/2021"
    When I add a location with name "Christchurch"
    And I retrieve the full description and geolocalisation coordinates for location "Christchurch" from an external API
    Then The retrieved description, latitude and longitude are added to the location
    And The event is persisted with the updated location