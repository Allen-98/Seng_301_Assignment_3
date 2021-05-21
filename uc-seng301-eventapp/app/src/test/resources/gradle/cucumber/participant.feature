#
# Created on Wed Apr 09 2021
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

Feature: U3 - Add participant to event

  Scenario: AC1 - Add one existing participant to an event by its name
    Given There is a participant with name "Allen"
    When I add "Allen" to an event as a participant
    Then This event has a participant names "Allen" that is persisted when saving the event


  Scenario: AC2 - Add a participant which not exist and create as a side effect with given name
    Given There is no participant with name "Allen"
    When I create a participant with name "Allen"
    Then There is a participant with name "Allen"


  Scenario: AC3 - Can not add empty participants or participants with invalid names
    Given There are no participants with name "Allen123" and ""
    When I create participants with name "Allen123" and ""
    Then I expect an exception that disallow me to create any of those
