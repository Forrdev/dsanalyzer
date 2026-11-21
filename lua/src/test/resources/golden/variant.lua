function Act(ai, goal, paramTbl)
    local dist = ai:GetDist(TARGET_ENE_0)
    local appDist = Att_Dist_max
    local dashDist = Att_Dist_max + 2
    Approach(ai, goal, appDist, dashDist, 0)
    local odds = GET_PARAM(paramTbl[1], 100)
    Watch(ai, goal)
    return odds
end

function Update(ai, goal)
    local l1 = goal:GetParam(0)
    local l2 = goal:GetParam(1)
    if l2 <= 0 then
        l2 = 20
    end
    if dist then
        local l3 = 3
    end
    local l4 = ai:GetNumber(1)
    ai:SetNumber(1, l4 + 1)
    for l5 = 1, l4 do
        Step(l5, l1)
    end
    local l6 = Check(l2)
    return l6
end

function Make(base)
    local l1 = base * 3
    return function(x)
        return x + l1
    end
end

Message = "\148.\0012"
