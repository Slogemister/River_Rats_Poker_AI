# Copyright (c) 2019 Eric Steinberger


"""
This file is not runable; it's is a template to show how you could play against your algorithms. To do so,
replace "YourAlgorithmsEvalAgentCls" with the EvalAgent subclass (not instance) of your algorithm.

Note that you can see the AI's cards on the screen since this is just a research application and not meant for actual
competition. The AI can, of course, NOT see your cards.
"""

import sys
sys.path.insert(0, "/Users/awitt/Documents/Spring_2023/Intelligent_Systems/River_Rats_Poker_AI/PokerRL/Agent")


from PokerRL.agent.rulesBased import rulesBased
from PokerRL.game.InteractiveGame import InteractiveGame

if __name__ == '__main__':
    
    #eval_agent = rulesBased.load_from_disk(
    #    path_to_eval_agent="C:\\Users\\awitt\\Documents\\Spring_2023\\Intelligent_Systems\\River_Rats_Poker_AI\\PokerRL\\Agent\\rulesBased.py")
    eval_agent = rulesBased()
    

    game = InteractiveGame(env_cls=eval_agent.env_bldr.env_cls,
                           env_args=eval_agent.env_bldr.env_args,
                           seats_human_plays_list=[0],
                           eval_agent=eval_agent,
                           )

    game.start_to_play()
