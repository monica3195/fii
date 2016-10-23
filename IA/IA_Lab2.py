
__author__ = 'dimitrie'

capra, lup, varza, human = ("Capre", "Lupi", "Verze", "Ion")

carriages = (capra, lup, varza, None)
forbiddenCombinations = (set((capra, lup)), set((varza, capra)))

solution_logging = []


# Check if current configuration is allowed
def checkForbiddenConfiguration(config):
    for side in config[0]:
        if human not in side:
            for forbidden in forbiddenCombinations:
                if side.issuperset(forbidden):
                    return True

    return False


# Finish when left configuration is an empty set
def finish(configuration):
    left,right = configuration[0]
    return set() == left

def transport(configuration, item): #item -> a carriages elemt to transport from source to destination
    left,right=[set(x) for x in configuration[0]]

    if human in left:
        src,dest=left,right
    else:
        src,dest=right,left

    if item and not item in src:
        return None

    description="Ion goes --> " if human in left else " Ion goes <-- "
    src.remove(human)
    dest.add(human)

    if item:
        src.remove(item)
        dest.add(item)
        description+=" with the "+item
    else:
        description+=" alone"
    return ((left,right), description) #return the result configration

def onegeneration(config):
    followups=[]
    for item in carriages:
        followup=transport(config, item)
        if not followup: continue
        followups.append(followup)
    return followups

def printConfiguration(config, level=0):
    left,right = config[0]
    configVerdict="Not allowed" if checkForbiddenConfiguration(config) else "Allowed"
    print "     "*level,",".join(left),"-----".join(right), config[1], configVerdict


def generateSolution(config, level=0):
    print "   "*level,config
    solution_logging.extend([None] * (level - len(solution_logging) + 1))
    solution_logging[level] = config[1]
    #printConfiguration(config, level)
    childs=onegeneration(config)
    for child in childs:
        if checkForbiddenConfiguration(child): #skip configuration which are forbidden  
            continue
        if child[0] in previous_configuration: #skip skip configuratin which have been seen before
            continue
        previous_configuration.append(child[0])
        generateSolution(child, level+1)
        


# Start configuration
current_configuration = ((set((human, capra, varza, lup)), set()), "")
previous_configuration = [current_configuration[0]]

print "To Transport:",carriages
print "Start state:",current_configuration
#print previous_configuration
print "Forbidden combinations :",forbiddenCombinations

generateSolution(current_configuration)

print "\n The solution to the problem:"
for step in solution_logging:
    if step:
        print " ",step
